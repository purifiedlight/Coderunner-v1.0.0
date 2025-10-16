const axios = require('axios');

const JAVA_ENGINE_URL = 'http://localhost:8080/execute';

/**
 * @param {string} code - Java code for execute.
 * @returns {Promise<object>} - An object with the output or an error.
 */
async function executeJavaCode(code) {
  console.log(`Sending code to Java Execution Engine at ${JAVA_ENGINE_URL}`);
  
  const payload = {
    language: 'java',
    code: code,
  };

  const response = await axios.post(JAVA_ENGINE_URL, payload, {
      headers: {
          'Content-Type': 'application/json'
      }
  });

  return response.data;
}

module.exports = {
  executeJavaCode,
};