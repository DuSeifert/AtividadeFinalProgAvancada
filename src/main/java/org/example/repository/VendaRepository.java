package org.example.repository;

import org.example.entities.User;
import org.example.entities.Venda;
import org.example.paymentMethods.PaymentType;

import java.sql.*;
import java.util.*;

public class VendaRepository implements EntityRepository<Venda> {

    private final Connection connection;

    public VendaRepository(Connection connection) {
        this.connection = connection;
    }


    @Override
    public void save(Venda entity){

        String query = "INSERT INTO vendas  VALUES (?, ?, ?, ?, ?)";
        try {
            PreparedStatement stmt = this.connection.prepareStatement(query);
            stmt.setString(1, entity.getUuid().toString());
            stmt.setString(2, entity.getCliente());
            stmt.setString(3, String.join("\\|", entity.getProdutos()));
            stmt.setDouble(4, entity.getTotal());
            stmt.setString(5, entity.getPaymentType().toString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Erro ao armazenar venda: " + e.getMessage());
        }

    }

    @Override
    public Optional<Venda> findById(UUID uuid) {
        String sql = "SELECT * FROM vendas WHERE uuid = ?";

        try {
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, uuid.toString());
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Venda v = new Venda(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("cliente"),
                        converterProdutos(rs.getString("produtos")),
                        rs.getDouble("total"),
                        converterPT(rs.getString("paymentType"))
                );
                return Optional.of(v);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar Venda: " + e.getMessage(), e);
        }

        return Optional.empty();
    }

    @Override
    public void deleteById(UUID uuid) {
        // Implementar método deleteById se necessário
    }

    @Override
    public List<Venda> findAll() {

        String sql = "SELECT * FROM vendas";
        List<Venda> listaVendas = new ArrayList<>();

        try {
            PreparedStatement stmt = this.connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Venda v = new Venda(
                        UUID.fromString(rs.getString("uuid")),
                        rs.getString("cliente"),
                        converterProdutos(rs.getString("produtos")),
                        rs.getDouble("total"),
                        converterPT(rs.getString("paymentType"))
                );
                listaVendas.add(v);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar as vendas: " + e.getMessage(), e);
        }

        return listaVendas;
    }

    public List<String> converterProdutos(String produtos) {
        String[] produtosArray = produtos.split("\\|");
        List<String> listaProdutos = new ArrayList<>();
        Collections.addAll(listaProdutos, produtosArray);
        return listaProdutos;
    }

    public PaymentType converterPT(String paymentType) {
        return switch (paymentType){
            case "PIX" -> PaymentType.PIX;
            case "CARTAO" -> PaymentType.CARTAO;
            case "BOLETO" -> PaymentType.BOLETO;
            default -> throw new IllegalStateException("Unexpected value: " + paymentType);
        };
    }





}


