import java.util.Scanner;

class Controle {

    // ***************************************************************************************
    // Atributos
    // ***************************************************************************************
    static View tela;
    static Jogador player;
    static GerenciadorMissoes missoes;

    static String mapa[][] =
    { 
        { "x", "x", "x", "x", "x", "x" },
        { "x", " ", " ", " ", " ", "x" },
        { "x", " ", " ", " ", " ", "x" },
        { "x", " ", " ", " ", " ", "x" },
        { "x", " ", " ", " ", " ", "x" },
        { "x", "x", "x", "x", "x", "x" }
    };

    public static int getPontos;

    // ***************************************************************************************
    // Operações/Métodos
    // ***************************************************************************************


    // ============================================================
    // Método que controla o laço principal do jogo
    // ============================================================


    public static void operacao() {

        //// Limpa a tela
        tela.limpar_tela();

        //// Mostra as missões ativas
        /* Obtém a lista de missões */
        String missoes_ativas = missoes.mostrar_missoes();

        /* Apresenta para o usuário */
        tela.mostrar_missoes_ativas(missoes_ativas);

        // Mostra os dados do usuario
        tela.mostrar_infos(player.get_x(), player.get_y());

        //Mostra os pontos do usuario
        tela.mostrarPontos(getPontos);

        //// Mostra o mapa
        tela.desenhar_mapa(mapa, player.get_x(), player.get_y());

        //// Pede um direção para andar
        String direcao = tela.obter_direcao();

        //// Processa a entrada do usuário
        processar_comando(direcao);

        
    }
    

    // ============================================================
    // Método que realiza os procedimentos necessários para cada
    // comando do usuário
    // ============================================================
    public static void processar_comando(String comando) {

        /* Nova posição caso se mova */
        int nova_posicao_x = player.get_x();
        int nova_posicao_y = player.get_y();

        String comandoUpp = comando.toUpperCase();

        if (comandoUpp.equals("A")) {
            nova_posicao_x--;
        } else if (comandoUpp.equals("D")) {
            nova_posicao_x++;
        } else if (comandoUpp.equals("W")) {
            nova_posicao_y--;
        } else if (comandoUpp.equals("S")) {
            nova_posicao_y++;
        } else if (comandoUpp.equals("Q")){
            tela.jogoEncerrado();
            System.exit(0);
        }
        
        /* Se tiver uma parede onde ele quer ir, não faz nada */
        if (mapa[nova_posicao_y][nova_posicao_x].equals("x")) {
            return;
        }

        /* Se não tiver parede, atualiza a posição do jogador */
        player.set_x(nova_posicao_x);
        player.set_y(nova_posicao_y);

        /* Verificar se tem uma missão no local (início ou fim) */
        missoes.verificar_missoes(player.get_x(), player.get_y());


    }

    // ============================================================
    // Verifica no gerenciador de missões se existe uma missão
    // na posição especificada
    // ============================================================
    public static boolean existe_missao(int x, int y) {
        return missoes.existe_missao(x, y);
    }

    // ***************************************************************************************
    // Main
    // ***************************************************************************************

    public static void main(String[] args) {
        //// Inicializa os atributos
        /* Interações com o usuário */ 
        tela = new View();

        /* Nosso jogador */
        player = new Jogador(1, 1);

        /* Nossas missões */
        missoes = new GerenciadorMissoes();

        tela.exibirTelaInicio();

        //// Inicia o jogo

        while(true) {
        operacao();

        boolean todasCompletas = missoes.todas_missoes_completas();
        if (todasCompletas) {
            tela.fimDeJogo();
            tela.mostrarPontos(getPontos);
            System.exit(0);
        }

    }
    }
}
        
        
