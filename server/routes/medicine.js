const router = require('express').Router()
const medicineModel = require('../models/Medicine.model')

router.route('/fetch/:text').get(async (req,res)=>{
    const query = req.params.text
    let medicines
    try {
        medicines = await medicineModel.find({name:new RegExp('^'+query,'i')},{_id:1,name:1,manufacturer:1,strength:1}).limit(20);
        res.json(medicines);
    } catch (error) {
        res.json(error);
    }
})

router.route('/:id').get(async (req,res)=>{
    console.log("here");
    const id = req.params.id
    let medicine
    try {
        medicine = await medicineModel.findById(id)
        res.json(medicine)
    } catch (error) {
        res.json(error)
    }
})

module.exports = router