package com.exo.authentification.dto;

public record SignUpDto (String firstName, String lastName, String login, char[] password ) {
}
