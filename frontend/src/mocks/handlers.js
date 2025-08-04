import { rest } from 'msw';

export const handlers = [
    //login simulado
    rest.post('/api/login', async (req, res, ctx) => {
        const { email, senha } = await req.json();

        if (email === 'teste@teste.com' && senha === '123456') {
            return res(
                ctx.status(200),
                ctx.json({ token: 'fake-jwt-token', nome: 'João' })
            );
        }

        return res(
            ctx.status(401),
            ctx.json({ message: 'Credenciais inválidas' })
        );
    }),

    //Cadastro simulado 
    rest.post('/api/usuarios', async (req, res, ctx) => {
        const { nome, email, senha } = await req.json();

        if (!nome || !email || !senha) {
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
            ctx.json({ id: 123, nome, email })
        );
    })
];
