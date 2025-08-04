import { rest } from 'msw';

export const handlers = [
    //login simulado
    rest.post('/auth', async (req, res, ctx) => {
        const { email, senha } = await req.json();

        if (email === 'teste@teste.com' && senha === '123456') {
            return res(
                ctx.status(200),
                ctx.json({ token: 'fake-jwt-token', nome: 'João', id: 123 })
            );
        }

        return res(
            ctx.status(401),
            ctx.json({ message: 'Credenciais inválidas' })
        );
    }),

    //Cadastro simulado 
    rest.post('/usuarios', async (req, res, ctx) => {
        const { nome, cpf, email, senha } = await req.json();

        if (!nome || !cpf || !email || !senha) {
            return res(
                ctx.status(400),
                ctx.json({ message: 'Dados incompletos' })
            );
        }

        if (email === 'ja@existe.com') {
            return res(
                ctx.status(409),
                ctx.json({ message: 'Usuário já cadastrado' })
            );
        }

        return res(
            ctx.status(201),
            ctx.json({ id: 123, nome, cpf, email })
        );
    }),

    //Conta corrente simulada
    rest.get('/conta/:id', (req, res, ctx) => {
        const { id } = req.params;
        if (id === '123') {
            return res(
                ctx.status(200),
                ctx.json({
                    nome: 'João',
                    numero: 123456,
                    agencia: 7890,
                    saldo: 2500.75
                })
            );
        }

        return res(
            ctx.status(404),
            ctx.json({ message: 'Conta não encontrada' })
        );
    })
];
