const express = require('express');
const cors = require('cors');
const bodyParser = require('body-parser');
const executeRoute = require('./routes/execute');

const app = express();
const PORT = process.env.PORT || 3000;

app.use(cors());
app.use(bodyParser.json());

app.use('/api', executeRoute);

app.get('/', (req, res) => {
  res.send('Node.js API Gateway for CodeRunner is running!');
});

app.listen(PORT, () => {
  console.log(`API Gateway is listening on port ${PORT}`);
});