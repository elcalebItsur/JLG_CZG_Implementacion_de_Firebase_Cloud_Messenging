const admin = require('firebase-admin');
const express = require('express');
const bodyParser = require('body-parser');
const path = require('path');

// Replace with the path to your service account key file
const serviceAccount = require('../serviceAccountKey.json');

admin.initializeApp({
  credential: admin.credential.cert(serviceAccount)
});

const app = express();
app.use(bodyParser.json());
app.use(express.static('public'));

app.post('/send-notification', (req, res) => {
  const { token, title, body } = req.body;

  if (!token || !title || !body) {
    return res.status(400).send({ error: 'Token, title, and body are required' });
  }

  const message = {
    notification: {
        title: title,
        body: body
    },
    android: {
        priority: 'high'
    },
    token: token
  };

  admin.messaging().send(message)
    .then((response) => {
      console.log('Successfully sent message:', response);
      res.send({ success: true, response: response });
    })
    .catch((error) => {
      console.log('Error sending message:', error);
      res.status(500).send({ error: error.message });
    });
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Server is running on http://localhost:${PORT}`);
});
