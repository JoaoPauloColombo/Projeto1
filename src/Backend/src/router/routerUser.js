const { Router } = require("express");
const UserController = require("../controller/UserController");
const { validateUser  , validateUserId } = require("../middlewares/ValidateUser ");

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

// Rota de cadastro de usuário
router.post('/', validateUser  , (req, res) => {
    // Descriptografa os dados recebidos
    const nomeDescriptografado = cifraDeCesarDescriptografar(req.body.nome, 3); // Deslocamento de 3
    const emailDescriptografado = cifraDeCesarDescriptografar(req.body.email, 3);
    const senhaDescriptografada = cifraDeCesarDescriptografar(req.body.senha, 3);

    // Cria um novo objeto com os dados descriptografados
    const usuarioDescriptografado = {
        nome: nomeDescriptografado,
        email: emailDescriptografado,
        senha: senhaDescriptografada,
    };

    // Passa os dados descriptografados para o UserController
    UserController.create(usuarioDescriptografado, res);
});

// Outras rotas
router.put('/:id', validateUser  , validateUserId, (req, res) => {
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