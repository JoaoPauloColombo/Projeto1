const validateComentario = (req, res, next) => {
    const { nome, descricao, nota } = req.body;

    if (!nome || typeof nome !== 'string') {
        return res.status(400).json({ msg: "Campo 'nome' é obrigatório e deve ser uma string." });
    }
    if (!descricao || typeof descricao !== 'string') {
        return res.status(400).json({ msg: "Campo 'descricao' é obrigatório e deve ser uma string." });
    }
    if (nota === undefined) { 
        return res.status(400).json({ msg: "Campo 'nota' é obrigatório." });
    }
    if (typeof nota !== 'number' || isNaN(nota) || nota < 0 || nota > 5) { 
        return res.status(400).json({ msg: "Campo 'nota' deve ser um número entre 0 e 5." });
    }

    return next();
};

const validateComentarioId = (req, res, next) => {
    const { id } = req.params;

    if (!id) {
        return res.status(400).json({ msg: "Parâmetro 'id' faltando." });
    }
    if (isNaN(id) || Number(id) <= 0) {
        return res.status(400).json({ msg: "Parâmetro 'id' deve ser um número positivo." });
    }

    return next();
};

module.exports = { validateComentario, validateComentarioId };