const { Router } = require("express");
const userRoutes = require("./routerUser");
const comentarioRoutes = require("./routerComentario");
const coordenadasRoutes = require("./routerCoordenadas");
const uploadRoutes = require('./routerUpload');

const router = Router();

// Rotas para os recursos
router.use('/comentarios', comentarioRoutes);

router.use('/images', uploadRoutes);

router.use('/users', userRoutes);

router.use('/coordenadas', coordenadasRoutes);

module.exports = router;