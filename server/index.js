const express = require('express');
const cors = require('cors');
const mongoose = require('mongoose')
const connection = require('./db/mongoose')

require('dotenv').config();

const app = express();

app.use(cors);
app.use(express.json());


const port = process.env.PORT || 5000

let gfs
connection.once('open', () => {
  gfs = new mongoose.mongo.GridFSBucket(connection.db, { bucketName: 'uploads' })
  app.locals.gfs = gfs
})

app.listen(port,()=>{
    console.log(`Listening on port number: ${port}`);
})
