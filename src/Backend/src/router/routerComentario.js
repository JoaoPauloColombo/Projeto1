const { Router } = require("express");
const ComentarioController = require("../controller/ComentarioController");
const { validateComentario, validateComentarioId } = require("../middlewares/ValidateComentario");
const authenticateToken = require("../middlewares/authenticateToken"); // Importa o middleware de autenticação

const router = Router();

// Rotas de Comentários
router.post('/', authenticateToken, validateComentario, (req, res) => {
    ComentarioController.create(req, res);
});

router.put('/:id', authenticateToken, validateComentario, validateComentarioId, (req, res) => {
    ComentarioController.update(req, res);
});

router.get('/', (req, res) => {
    ComentarioController.getAll(req, res);
});

router.get('/:id', validateComentarioId, (req, res) => {
    ComentarioController.getOne(req, res);
});

router.delete('/:id', authenticateToken, validateComentarioId, (req, res) => {
    ComentarioController.delete(req, res);
});

module.exports = router;