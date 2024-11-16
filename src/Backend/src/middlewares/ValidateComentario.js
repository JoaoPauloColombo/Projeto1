const validateComentario = (req, res, next) => {
    const { nome, descricao, nota } = req.body;

    if (!nome) {
        return res.status(400).json({ msg: "Campo 'nome' é obrigatório." });
    }
    if (!descricao) {
        return res.status(400).json({ msg: "Campo 'descricao' é obrigatório." });
    }
    if (nota === undefined) { 
        return res.status(400).json({ msg: "Campo 'nota' é obrigatório." });
    }
    if (typeof nota !== 'number' || nota < 0 || nota > 5) { 
        return res.status(400).json({ msg: "Campo 'nota' deve ser um número entre 0 e 5." });
    }

    return next();
};

const validateComentarioId = (req, res, next) => {
    const { id } = req.params;

    if (!id) {
        return res.status(400).json({ msg: "Parâmetro 'id' faltando." });
    }
    if (isNaN(id)) {
        return res.status(400).json({ msg: "Parâmetro 'id' deve ser um número." });
    }

    return next();
};

module.exports = { validateComentario, validateComentarioId };