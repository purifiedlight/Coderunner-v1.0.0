package com.portfolio.coderunner.service;

import com.portfolio.coderunner.dto.ExecutionResponse;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
public class ExecutionService {

    private static final long TIME_LIMIT_SECONDS = 5;

    public ExecutionResponse execute(String code) {
        Path tempDir = null;
        try {
            tempDir = Files.createDirectory(Paths.get("temp").resolve(UUID.randomUUID().toString()));
            Path sourceFile = tempDir.resolve("Main.java");
            Files.write(sourceFile, code.getBytes());

            ProcessBuilder compilerProcessBuilder = new ProcessBuilder("javac", sourceFile.toString());
            Process compilerProcess = compilerProcessBuilder.start();

            if (!compilerProcess.waitFor(TIME_LIMIT_SECONDS, TimeUnit.SECONDS)) {
                compilerProcess.destroyForcibly();
                return new ExecutionResponse(null, "Compilation timed out.", false);
            }

            if (compilerProcess.exitValue() != 0) {
                String compilationError = readStream(compilerProcess.getErrorStream());
                return new ExecutionResponse(null, compilationError, false);
            }

            ProcessBuilder runnerProcessBuilder = new ProcessBuilder("java", "-cp", tempDir.toString(), "Main");
            Process runnerProcess = runnerProcessBuilder.start();

            if (!runnerProcess.waitFor(TIME_LIMIT_SECONDS, TimeUnit.SECONDS)) {
                runnerProcess.destroyForcibly();
                return new ExecutionResponse(null, "Execution timed out.", false);
            }

            String output = readStream(runnerProcess.getInputStream());
            String error = readStream(runnerProcess.getErrorStream());

            if (!error.isEmpty()) {
                return new ExecutionResponse(output, error, false);
            }

            return new ExecutionResponse(output, null, true);

        } catch (IOException | InterruptedException e) {
            return new ExecutionResponse(null, "An internal server error occurred: " + e.getMessage(), false);
        } finally {
            if (tempDir != null) {
                try {
                    Files.walk(tempDir)
                        .sorted((a, b) -> b.compareTo(a))
                        .forEach(path -> {
                            try {
                                Files.delete(path);
                            } catch (IOException e) {
                                // Log error, but continue cleanup
                            }
                        });
                } catch (IOException e) {
                    // Log error if walk fails
                }
            }
        }
    }

    private String readStream(InputStream stream) throws IOException {
        StringBuilder sb = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
        }
        return sb.toString().trim();
    }
}