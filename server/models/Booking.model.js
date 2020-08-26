const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const bookingSchema = new Schema({
    customer_id:{type:Schema.Types.ObjectId},
    shop_id:{type:Schema.Types.ObjectId},
    medicine_id:{type:Schema.Types.ObjectId},
    booking_amount:{type:String},
    time_range:{type:Number}
},{
    timestamps:true
});

const Booking = mongoose.model('bookings',bookingSchema);

module.exports = Booking