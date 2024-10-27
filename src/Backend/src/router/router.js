const { Router } = require("express");
const userRoutes = require("./routerUser");
const comentarioRoutes = require("./routerComentario");
const coordenadasRoutes = require("./routerCoordenadas");
const authenticateToken = require("../middlewares/authenticateToken");

const uploadRoutes = require('./routerUpload');

const router = Router();

router.use('/comentario', comentarioRoutes);

router.use('/image', uploadRoutes);

router.use('/user', userRoutes);

router.use('/coordenadas', coordenadasRoutes);


module.exports = router;