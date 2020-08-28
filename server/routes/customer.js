const router = require('express').Router()
const customerModel = require('../models/Customer.model')
const shopModel = require('../models/Shop.model')

router.route('/login').post(async(req, res)=>{
    const query = {
        email: req.body.email,
        password: req.body.password,
        isCustomer: req.body.isCustomer
    }

    try {
        if(isCustomer) {
            user = await customerModel.find({email_id: query.email, password: query.password})
        } else {
            user = await shopModel.find({email_id: query.email, password: query.password})
        }
        res.status(200).json(user)
    } catch (error) {
        res.json(error)
    }
})

router.route('/register').post((req,res)=>{
    let newUser = {}
    console.log(req.body);
    if(req.body.isCustomer===true) {
        newUser = new customerModel({
            name: req.body.name,
            email: req.body.email,
            password: req.body.password,
            phone: req.body.phone
        })
    } else {
        newUser = new shopModel({
            name: req.body.name,
            email: req.body.email,
            password: req.body.password,
            address: req.body.address,
            phone: req.body.phone,
            license: req.body.license
        })
    }

    newUser.save((error, user) => {
        if (error) {

            res.json(error)
        } else {
            console.log("registration successful");
            res.json({"success":"true"})
            // res.json("Successfully Registered")
        }
    })
})

module.exports = router