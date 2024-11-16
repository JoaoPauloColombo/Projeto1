const { Router } = require("express");
const ComentarioController = require("../controller/ComentarioController");
const { validateComentario, validateComentarioId } = require("../middlewares/ValidateComentario");
const { authenticate } = require("../middlewares/authenticateToken");

const router = Router();

// Rotas de Comentários
router.post('/', authenticate, validateComentario, (req, res) => {
    ComentarioController.create(req, res);
});

router.put('/:id', authenticate, validateComentario, validateComentarioId, (req, res) => {
    ComentarioController.update(req, res);
});

router.get('/', (req, res) => {
    ComentarioController.getAll(req, res);
});

router.get('/:id', validateComentarioId, (req, res) => {
    ComentarioController.getOne(req, res);
});

router.delete('/:id', authenticate, validateComentarioId, (req, res) => {
    ComentarioController.delete(req, res);
});

module.exports = router;