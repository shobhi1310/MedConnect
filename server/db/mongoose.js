const mongoose = require('mongoose')
require('dotenv').config()

const uri = process.env.URI

mongoose.connect(uri, { useUnifiedTopology: true, useNewUrlParser: true, useCreateIndex: true})

const connection = mongoose.connection
connection.once('open', () => {
  console.log('MongoDB database connection established successfully')
})

module.exports = connection