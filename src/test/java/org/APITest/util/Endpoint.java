package org.APITest.util;

public class Endpoint {

    public static final String user = "/user";
    public static final String get_username = "/user/{username}";
    public static final String login = "/user/login";
    public static final String logout = "/user/logout";
    public static final String pets = "/pet";
    public static final String get_pets = "/pet/{petId}";
    public static final String pet_image = "/pet/{petId}/uploadImage";
    public static final String pet_status = "/pet/findByStatus";
    public static final String pets_tags = "/pet/findByTags";
    public static final String order = "/store/order";
    public static final String concluirCompra = "/carrinhos/concluir-compra";
    public static final String cancelarCompra = "/carrinhos/cancelar-compra";
}