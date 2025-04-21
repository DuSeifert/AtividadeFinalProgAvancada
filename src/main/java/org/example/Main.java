package org.example;

import org.example.entities.Product;
import org.example.entities.Venda;
import org.example.paymentMethods.PaymentMethod;
import org.example.paymentMethods.PaymentType;
import org.example.repository.ProductRepository;
import org.example.entities.User;
import org.example.repository.UserRepository;
import org.example.repository.VendaRepository;

import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {
        ProductRepository listaProdutos = null;
        UserRepository listaUsuarios = null;
        VendaRepository listaVendas = null;
        Connection conn = null;

        String url = "jdbc:sqlite:database.sqlite";

        try {
            conn = DriverManager.getConnection(url);
            if (conn != null) {
                listaProdutos = new ProductRepository(conn);
                listaUsuarios = new UserRepository(conn);
                listaVendas = new VendaRepository(conn);
            } else {
                System.out.println("Falha na conexão.");
                System.exit(1);
            }
        } catch (SQLException e) {
            System.out.println("Erro ao conectar: " + e.getMessage());
            System.exit(1);
        }

        Scanner scanner = new Scanner(System.in);
        int opc;

        do {
            System.out.println("\n----- TERMINAL -----\n");
            System.out.println("1 --- Cadastrar Produto");
            System.out.println("2 --- Listas Produtos");
            System.out.println("3 --- Procurar Produto");
            System.out.println("4 --- Deletar Produto\n");

            System.out.println("5 --- Cadastrar Usuário");
            System.out.println("6 --- Listar Usuários");
            System.out.println("7 --- Procurar Usuário");
            System.out.println("8 --- Deletar Usuário\n");

            System.out.println("9 --- Realizar Venda");
            System.out.println("10 -- Listar Vendas");
            System.out.println("11 -- Procurar Venda\n");

            System.out.println("0 -- Sair");
            opc = scanner.nextInt();
            scanner.nextLine();

            switch (opc) {

                // Product

                case 1:
                    System.out.println("Cadastrar Produto");
                    listaProdutos.save(new Product(novoNome(scanner), novoPreco(scanner), calcular_ID()));

                    break;
                case 2:
                    System.out.println("Listar Produtos");
                    List<Product> products = listaProdutos.findAll();
                    products.forEach(System.out::println);
                    break;
                case 3:
                    System.out.println("Procurar Produto");
                    System.out.print("Digite o UUID do produto: ");
                    String findIDProduto = scanner.nextLine();

                    Product produtoFindByID;

                    if(findIDProduto.length() == 36){
                        produtoFindByID = listaProdutos.findById(UUID.fromString(findIDProduto)).orElse(null);
                    }
                    else{
                        produtoFindByID = listaProdutos.findById(Integer.parseInt(findIDProduto)).orElse(null);
                    }

                    if (produtoFindByID == null) {
                        System.out.println("Produto não encontrado.");
                    } else {
                        System.out.println("Produto encontrado: " + produtoFindByID);
                    }
                    break;
                case 4:
                    System.out.println("Deletar Produto");
                    System.out.print("Digite o UUID do produto: ");
                    String deleteIDProduto = scanner.nextLine();

                    if(deleteIDProduto.length() == 36){
                        listaProdutos.deleteById(UUID.fromString(deleteIDProduto));
                    }
                    else{
                        listaProdutos.deleteById(Integer.parseInt(deleteIDProduto));
                    }
                    System.out.println("Produto deletado com sucesso.");

                    break;

                // User

                case 5:
                    System.out.println("Cadastrar Usuário");
                    listaUsuarios.save(new User(novoNome(scanner), novoEmail(scanner), novaSenha(scanner)));
                    break;
                case 6:
                    System.out.println("Listar Usuários");
                    List<User> users = listaUsuarios.findAll();
                    users.forEach(System.out::println);
                    break;

                case 7:
                    System.out.println("Procurar Usuário");
                    System.out.print("Digite o UUID do Usuário: ");
                    String findIDUser = scanner.nextLine();

                    User UserFinByID = listaUsuarios.findById(UUID.fromString(findIDUser)).orElse(null);



                    if (UserFinByID == null) {
                        System.out.println("Usuário não encontrado.");
                    } else {
                        System.out.println("Usuário encontrado: " + UserFinByID);
                    }
                    break;

                case 8:
                    System.out.println("Deletar Usuário");
                    System.out.print("Digite o UUID do Usuário: ");
                    String deleteIDUser = scanner.nextLine();

                    listaUsuarios.deleteById(UUID.fromString(deleteIDUser));
                    System.out.println("Usuário deletado com sucesso.");

                    break;

                // Venda

                case 9:
                    System.out.println("\nRealizar Venda");
                    System.out.print("Digite o Email do usuário: ");
                    String userEmail = scanner.nextLine();

                    User userFound = listaUsuarios.findByEmail(userEmail).orElse(null);

                    if(userFound == null) {
                        System.out.println("Usuário não encontrado.");
                        break;
                    }

                    System.out.println("Usuário encontrado: " + userFound.getName());

                    System.out.print("\nDigite o ID do produto (Separados por vírgulas): ");
                    String[] produtosVenda = scanner.nextLine().split(",");

                    List<String> mostrarListaVenda = new java.util.ArrayList<>();
                    double total = 0;

                    Product produtoVenda;

                    for (String s : produtosVenda) {

                        if(s.length() == 36){
                            produtoVenda = listaProdutos.findById(UUID.fromString(s)).orElse(null);
                        }
                        else{
                            produtoVenda = listaProdutos.findById(Integer.parseInt(s)).orElse(null);
                        }

                        if (produtoVenda != null) {
                            String armazenar = produtoVenda.getName() + " ( R$" + produtoVenda.getPrice() + " )";
                            System.out.println("Produto encontrado: - "+ armazenar);
                            mostrarListaVenda.add(armazenar);
                            total += produtoVenda.getPrice();
                        }
                        else{
                            System.out.println("Produto " + s + " não encontrado.");
                        }
                    }

                    System.out.println("Escolha a forma de pagamento:");
                    System.out.println("  1 -- PIX");
                    System.out.println("  2 -- BOLETO");
                    System.out.println("  3 -- CARTÃO");

                    int op;
                    do{
                        op = scanner.nextInt();
                        scanner.nextLine();
                    }while(op<1 || op>3);

                    PaymentType pt = switch (op){
                        case 1 -> PaymentType.PIX;
                        case 2 -> PaymentType.BOLETO;
                        case 3 -> PaymentType.CARTAO;
                        default -> throw new IllegalStateException("Unexpected value: " + op);
                    };

                    PaymentMethod paymentMethod = PaymentMethodFactory.create(pt);

                    System.out.println("Aguardando o pagamento ...\n");
                    paymentMethod.pay(total);

                    Venda venda = new Venda(userFound.getName(), mostrarListaVenda, total, pt);
                    System.out.println("\nResumo da venda:");
                    venda.resumoVenda();

                    listaVendas.save(venda);
                    System.out.println("\nVenda realizada com sucesso!");

                    break;

                case 10:
                    System.out.println("Listar Vendas\n");
                    List<Venda> vendas = listaVendas.findAll();
                    vendas.forEach(System.out::println);
                    break;

                case 11:
                    System.out.println("Procurar Venda");
                    System.out.print("Digite o UUID da venda: ");
                    String findIDVenda = scanner.nextLine();

                    Venda vendaFindByID = listaVendas.findById(UUID.fromString(findIDVenda)).orElse(null);

                    if (vendaFindByID == null) {
                        System.out.println("Venda não encontrada.");
                    } else {
                        System.out.println("Venda encontrada: " + vendaFindByID);
                    }
                    break;

                default:
                    if(opc != 0) {
                        System.out.println("Opção inválida. Tente novamente");
                    }
                    break;
            }

        } while (opc != 0);

        scanner.close();
        try {
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private static String novoNome(Scanner scanner) {
        System.out.print("Entre o nome: ");
        return scanner.nextLine();
    }

    private static double novoPreco(Scanner scanner) {
        System.out.print("Preço do Produto: ");
        double preco = scanner.nextDouble();
        scanner.nextLine();

        return preco;
    }

    private static String novoEmail(Scanner scanner) {
        System.out.print("Email do Usuário: ");
        return scanner.nextLine();
    }

    private static String novaSenha(Scanner scanner) {
        System.out.print("Senha do Usuário: ");
        return scanner.nextLine();
    }

    private static int calcular_ID() {
        int id = 0;
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:database.sqlite");
            String query = "SELECT MAX(id) AS max_id FROM products";
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                id = rs.getInt("max_id") + 1;
            }

            conn.close();
        } catch (SQLException e) {
            System.out.println("Erro ao calcular ID: " + e.getMessage());
        }
        return id;
    }


}