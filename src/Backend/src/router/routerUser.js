const { Router } = require("express");
const UserController = require("../controller/UserController");
const { validateUser, validateUserId } = require("../middlewares/ValidateUser");
const bcrypt = require("bcryptjs");

const router = Router();

router.post('/', validateUser, async (req, res) => {
    console.log('Corpo da requisição:', req.body);


    if (!req.body) {
        return res.status(400).json({ error: "Corpo da requisição não pode estar vazio." });
    }


    const senhaHash = await bcrypt.hash(req.body.senha, 10);


    const usuario = {
        nome: req.body.nome,
        email: req.body.email,
        senha: senhaHash,
    };


    UserController.create(req, res, usuario);
});


router.put('/:id', validateUser, validateUserId, async (req, res) => {
    if (req.body.senha) {
        req.body.senha = await bcrypt.hash(req.body.senha, 10);
    }
    UserController.update(req, res);
});

router.get('/', (req, res) => {
    UserController.getAll(req, res);
});

router.get('/:id', validateUserId, (req, res) => {
    UserController.getOne(req, res);
});

router.delete('/:id', validateUserId, (req, res) => {
    UserController.delete(req, res);
});

router.post('/login', async (req, res) => {
    UserController.login(req, res);
});

module.exports = router;