const router = require('express').Router()
const shopModel = require('../models/Shop.model')

router.route('/:id').get(async (req,res)=>{
    const id = req.params.id
    let shop
    try {
        shop = await shopModel.findById(id)
        res.json(shop)
    } catch (error) {
        res.json(error)
    }
})

router.route('/medicinelist/:id').get(async (req,res)=>{
    const id = req.params.id
    let shop
    try {
        shop = await shopModel.findById(id,{medicines:1}).populate('medicines')
        res.json(shop)
    } catch (error) {
        res.json(error)
    }
})

module.exports = router