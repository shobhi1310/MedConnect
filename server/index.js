const express = require('express');
const cors = require('cors');
const mongoose = require('mongoose')
// const connection = require('./db/mongoose')

require('dotenv').config();

const app = express();
const medicineRouter = require('./routes/medicine')


app.use(cors);
app.use(express.json());
app.use('/medicine',medicineRouter);

const port = process.env.PORT || 5000

const uri = process.env.URI;

mongoose.connect(uri, { useNewUrlParser: true, useCreateIndex: true });
const connection = mongoose.connection;
connection.once('open', () => {
  console.log("MongoDB database connection established successfully");
})


// let gfs
// connection.once('open', () => {
//   gfs = new mongoose.mongo.GridFSBucket(connection.db, { bucketName: 'uploads' })
//   app.locals.gfs = gfs
// })

app.get('/',(req,res)=>{
  console.log("hello!!");
  res.json("Hello!!");
})

app.listen(port,()=>{
    console.log(`Listening on port number: ${port}`);
})
