const { Router } = require("express");
const CoordenadasController = require("../controller/CoordenadasController");
const { validateCoordenadas, validateCoordenadasId } = require("../middlewares/ValidateCoordenadas");
const { authenticate } = require("../middlewares/authenticateToken");

const router = Router();

// Rotas de Coordenadas
router.post('/', authenticate, validateCoordenadas, (req, res) => {
    CoordenadasController.create(req, res);
});

router.put('/:id', authenticate, validateCoordenadasId, validateCoordenadas, (req, res) => {
    CoordenadasController.update(req, res);
});

router.get('/', (req, res) => {
    CoordenadasController.getAll(req, res);
});

router.get('/:id', validateCoordenadasId, (req, res) => {
    CoordenadasController.getOne(req, res);
});

router.delete('/:id', authenticate, validateCoordenadasId, (req, res) => {
    CoordenadasController.delete(req, res);
});

module.exports = router;