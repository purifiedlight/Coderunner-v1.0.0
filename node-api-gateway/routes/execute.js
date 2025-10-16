const express = require('express');
const router = express.Router();
const javaRunner = require('../services/javaRunner');

router.post('/execute', async (req, res) => {
  const { language = 'java', code } = req.body;

  if (!code) {
    return res.status(400).json({ error: 'Code is required.' });
  }

  if (language !== 'java') {
    return res.status(400).json({ error: 'Only Java is supported at the moment.' });
  }

  try {
    const result = await javaRunner.executeJavaCode(code);
    res.json(result);
  } catch (error) {
    const errorMessage = error.response ? error.response.data : { error: 'Could not connect to the execution engine.' };
    res.status(500).json(errorMessage);
  }
});

module.exports = router;