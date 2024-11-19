const validateComentario = (req, res, next) => {
    const { nome, descricao, nota } = req.body;

    // Validação do campo 'nome'
    if (!nome || typeof nome !== 'string' || nome.trim().length === 0) {
        return res.status(400).json({ msg: "Campo 'nome' é obrigatório e deve ser uma string não vazia." });
    }
    // Limite de comprimento do nome (opcional)
    if (nome.length > 100) {
        return res.status(400).json({ msg: "Campo 'nome' deve ter no máximo 100 caracteres." });
    }

    // Validação do campo 'descricao'
    if (!descricao || typeof descricao !== 'string' || descricao.trim().length === 0) {
        return res.status(400).json({ msg: "Campo 'descricao' é obrigatório e deve ser uma string não vazia." });
    }
    // Limite de comprimento da descrição (opcional)
    if (descricao.length > 500) {
        return res.status(400).json({ msg: "Campo 'descricao' deve ter no máximo 500 caracteres." });
    }

    // Validação do campo 'nota'
    if (nota === undefined) { 
        return res.status(400).json({ msg: "Campo 'nota' é obrigatório." });
    }
    if (typeof nota !== 'number' || isNaN(nota) || !Number.isInteger(nota) || nota < 0 || nota > 5) { 
        return res.status(400).json({ msg: "Campo 'nota' deve ser um número inteiro entre 0 e 5." });
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