const express = require('express');
const cors = require('cors');
const connection = require('./db/mongoose')

require('dotenv').config();

const app = express();

app.use(cors);
app.use(express.json());


const port = process.env.PORT || 5000


app.listen(port,()=>{
    console.log(`Listening on port number: ${port}`);
})
