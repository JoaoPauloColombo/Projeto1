const { Router } = require("express");
const CoordenadasController = require("../controller/CoordenadasController");
const { validateCoordenadas, validateCoordenadasId } = require("../middlewares/ValidateCoordenadas");
const authenticateToken = require("../middlewares/authenticateToken");

const router = Router();

// Rotas de Coordenadas
router.post('/', authenticateToken, validateCoordenadas, (req, res) => {
    CoordenadasController.create(req, res);
});

router.put('/:id', authenticateToken, validateCoordenadasId, validateCoordenadas, (req, res) => {
    CoordenadasController.update(req, res);
});

router.get('/', (req, res) => {
    CoordenadasController.getAll(req, res);
});

router.get('/:id', validateCoordenadasId, (req, res) => {
    CoordenadasController.getOne(req, res);
});

router.delete('/:id', authenticateToken, validateCoordenadasId, (req, res) => {
    CoordenadasController.delete(req, res);
});

module.exports = router;