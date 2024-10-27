const validateCoordenadas = (req, res, next) => {
    const { latitude, longitude, nome } = req.body;

    // Verifica se os campos estão presentes e se latitude e longitude são números
    if (typeof latitude !== 'number' || typeof longitude !== 'number' || !nome) {
        return res.status(400).json({
            msg: "Campos inválidos: 'latitude', 'longitude' devem ser números e 'nome' não pode estar vazio.",
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