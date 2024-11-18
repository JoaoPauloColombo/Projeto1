const { Router } = require("express");
const UserController = require("../controller/UserController");
const { validateUser , validateUserId } = require("../middlewares/ValidateUser");

const router = Router();

// Rota para criar um novo usuário
router.post('/', validateUser , async (req, res) => {
    console.log('Corpo da requisição:', req.body); // Log para depuração

    // Verifique se o corpo da requisição está definido e contém os campos necessários
    const { nome, email, senha } = req.body;
    if (!nome || !email || !senha) {
        return res.status(400).json({ error: "Nome, email e senha são obrigatórios." });
    }

    // Cria um novo usuário usando o UserController
    await UserController.create(req, res);
});

// Outras rotas
router.put('/:id', validateUser , validateUserId, async (req, res) => {
    await UserController.update(req, res);
});

router.get('/', async (req, res) => {
    await UserController.getAll(req, res);
});

router.get('/:id', validateUserId, async (req, res) => {
    await UserController.getOne(req, res);
});

router.delete('/:id', validateUserId, async (req, res) => {
    await UserController.delete(req, res);
});

// Rota de login
router.post('/login', async (req, res) => {
    console.log('Corpo da requisição de login:', req.body); // Log para depuração

    const { email, senha } = req.body;
    if (!email || !senha) {
        return res.status(400).json({ error: "Email e senha são obrigatórios." });
    }

    // Passa os dados recebidos para o UserController para verificar o login
    await UserController.login(req, res);
});

module.exports = router;