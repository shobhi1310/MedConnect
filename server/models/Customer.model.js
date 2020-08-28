const mongoose = require('mongoose')
const Schema = mongoose.Schema

const customerSchema = new Schema({
    name:{type:String},
    email_id:{type:String,unique:true},
    phone:{type:String},
    password:{type:String},
    booking_current:[{
        type:Schema.Types.ObjectId,
        ref:'bookings'
    }],
    booking_history:[{
        type:Schema.Types.ObjectId,
        ref:'bookings'
    }]
})

const Customer = mongoose.model('customers',customerSchema)

module.exports = Customer