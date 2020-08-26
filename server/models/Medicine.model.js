const mongoose = require('mongoose');
const Schema = mongoose.Schema;

const medicineSchema = new Schema({
    name:{type:String},
    image_url:{type:String},
    manufacturer:{type:String},
    strength:{type:String},
    prescription:{type:Boolean},
    shops:[{
        type:Schema.Types.ObjectId,
        ref:'Shops'
    }]
})

const Medicine = mongoose.model('medicines',medicineSchema);

module.exports = Medicine;