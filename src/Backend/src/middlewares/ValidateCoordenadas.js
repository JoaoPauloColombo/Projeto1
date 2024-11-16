const validateCoordenadas = (req, res, next) => {
    const { latitude, longitude, nome } = req.body;

    // Verifica se os campos estão presentes
    if (latitude === undefined) {
        return res.status(400).json({
            msg: "Campo 'latitude' é obrigatório.",
        });
    }

    if (longitude === undefined) {
        return res.status(400).json({
            msg: "Campo 'longitude' é obrigatório.",
        });
    }

    if (!nome) {
        return res.status(400).json({
            msg: "Campo 'nome' é obrigatório.",
        });
    }

    return next();
};

const validateCoordenadasId = (req, res, next) => {
    const { id } = req.params;

    // Verifica se o ID está presente e se é um número
    if (!id || isNaN(id)) {
        return res.status(400).json({
            msg: "Parâmetro 'id' faltando ou inválido.",
        });
    }

    return next();
};

module.exports = { validateCoordenadas, validateCoordenadasId };