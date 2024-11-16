const { Router } = require("express");
const UserController = require("../controller/UserController");
const { validateUser, validateUserId } = require("../middlewares/ValidateUser");

const router = Router();

// Função de descriptografia da cifra de César
function cifraDeCesarDescriptografar(texto, deslocamento) {
    let resultado = '';

    for (let i = 0; i < texto.length; i++) {
        let c = texto.charAt(i);

        // Descriptografa apenas letras
        if (/[a-zA-Z]/.test(c)) {
            let base = c >= 'a' && c <= 'z' ? 'a' : 'A';
            c = String.fromCharCode((c.charCodeAt(0) - deslocamento - base.charCodeAt(0) + 26) % 26 + base.charCodeAt(0));
        }

        resultado += c;
    }

    return resultado;
}

router.post('/', validateUser, (req, res) => {
    console.log('Corpo da requisição:', req.body); // Log para depuração

    // Verifique se o corpo da requisição está definido e contém os campos necessários
    const { nome, email, senha } = req.body;
    if (!nome || !email || !senha) {
        return res.status(400).json({ error: "Nome, email e senha são obrigatórios." });
    }

    // Descriptografa os dados recebidos
    const nomeDescriptografado = cifraDeCesarDescriptografar(nome, 3);
    const emailDescriptografado = cifraDeCesarDescriptografar(email, 3);
    const senhaDescriptografada = cifraDeCesarDescriptografar(senha, 3);

    // Log dos dados descriptografados
    console.log('Dados descriptografados:', {
        nome: nomeDescriptografado,
        email: emailDescriptografado,
        senha: senhaDescriptografada
    });

    // Cria um novo objeto com os dados descriptografados
    const usuarioDescriptografado = {
        nome: nomeDescriptografado,
        email: emailDescriptografado,
        senha: senhaDescriptografada,
    };

    // Passa os dados descriptografados para o UserController
    UserController.create(req, res, usuarioDescriptografado);
});

// Outras rotas
router.put('/:id', validateUser, validateUserId, (req, res) => {
    UserController.update(req, res);
});

router.get('/', (req, res) => {
    UserController.getAll(req, res);
});

router.get('/:id', validateUserId, (req, res) => {
    UserController.getOne(req, res);
});

router.delete('/:id', validateUserId, (req, res) => {
    UserController.delete(req, res);
});

router.post('/login', (req, res) => {
    UserController.login(req, res);
});

module.exports = router;