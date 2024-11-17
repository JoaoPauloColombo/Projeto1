const { Router } = require("express");
const UserController = require("../controller/UserController");
const { validateUser , validateUserId } = require("../middlewares/ValidateUser");

const router = Router();

// Função de criptografia da cifra de César
function cifraDeCesar(texto, deslocamento) {
    let resultado = '';

    for (let i = 0; i < texto.length; i++) {
        let c = texto.charAt(i);
        // Criptografa apenas letras
        if (/[a-zA-Z]/.test(c)) {
            let base = c >= 'a' && c <= 'z' ? 'a' : 'A';
            c = String.fromCharCode((c.charCodeAt(0) + deslocamento - base.charCodeAt(0)) % 26 + base.charCodeAt(0));
        }
        resultado += c;
    }
    return resultado;
}

// Função de descriptografia da cifra de César
function cifraDeCesarDescriptografar(texto, deslocamento) {
    return cifraDeCesar(texto, -deslocamento); // Inverte o deslocamento para descriptografar
}

router.post('/', validateUser , (req, res) => {
    console.log('Corpo da requisição:', req.body); // Log para depuração

    // Verifique se o corpo da requisição está definido e contém os campos necessários
    const { nome, email, senha } = req.body;
    if (!nome || !email || !senha) {
        return res.status(400).json({ error: "Nome, email e senha são obrigatórios." });
    }

    // Criptografa os dados recebidos
    const nomeCriptografado = cifraDeCesar(nome, 3);
    const emailCriptografado = cifraDeCesar(email, 3);
    const senhaCriptografada = cifraDeCesar(senha, 3);

    // Cria um novo objeto com os dados criptografados
    const usuarioCriptografado = {
        nome: nomeCriptografado,
        email: emailCriptografado,
        senha: senhaCriptografada,
    };

    // Passa os dados criptografados para o UserController
    UserController.create(req, res, usuarioCriptografado);
});

// Outras rotas
router.put('/:id', validateUser , validateUserId, (req, res) => {
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

// Rota de login com descriptografia
router.post('/login', (req, res) => {
    console.log('Corpo da requisição de login:', req.body); // Log para depuração

    const { email, senha } = req.body;
    if (!email || !senha) {
        return res.status(400).json({ error: "Email e senha são obrigatórios." });
    }

    // Descriptografa os dados recebidos no login
    const emailDescriptografado = cifraDeCesarDescriptografar(email, 3);
    const senhaDescriptografada = cifraDeCesarDescriptografar(senha, 3);

    // Log dos dados descriptografados
    console.log('Dados descriptografados para login:', {
        email: emailDescriptografado,
        senha: senhaDescriptografada
    });

    // Passa os dados descriptografados para o UserController para verificar o login
    UserController.login(req, res, { email: emailDescriptografado, senha: senhaDescriptografada });
});

module.exports = router;