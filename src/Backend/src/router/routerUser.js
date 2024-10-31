const express = require('express');
const router = express.Router();
const UserController = require('../controller/UserController');
const { validateUser , validateUserId } = require('../middlewares/ValidateUser');

// Rota para criar um usuário
router.post('/create', validateUser , UserController.create);

// Rota para login
router.post('/login', UserController.login);

// Rota para atualizar um usuário
router.put('/update/:id', validateUserId, UserController.update);

// Rota para obter todos os usuários
router.get('/', UserController.getAll);

// Rota para obter um usuário específico
router.get('/:id', validateUserId, UserController.getOne);

// Rota para deletar um usuário
router.delete('/:id', validateUserId, UserController.delete);

module.exports = router;