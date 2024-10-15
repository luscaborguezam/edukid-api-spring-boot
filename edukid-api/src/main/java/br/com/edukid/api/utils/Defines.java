package br.com.edukid.api.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * CLASSE CRIADA PARA ARAZENAR AS CONSTANTES UTILIZADAS NO PROJETO 
 * @Author LUCAS BORGUEZAM
 * @Sice 9 de jul. de 2024
 */
public class Defines {

	/**
	 * CONSTANTES SOBRE SITUAÇÃO DA CONTA PAI
	 */
	public static final String STATUS_ATIVO_ACCOUNT_USER_FATHER = "ativo";
	public static final String STATUS_DESATIVADO_ACCOUNT_USER_FATHER = "desativado";
	
	/**
	 * CONSTANTES SOBRE A SITUAÇÃO DO QUIZ CRIADO
	 */
	public static final int QUIZ_EM_ABERTO = 0;
	public static final int QUIZ_FINALIZADO = 1;
	public static final int QUIZ_FINALIZADO_E_INCOMPLETO = 2;
	public static final int QUIZ_NAO_REALIZADO = 3;
	
	
	/**
	 * LISTA DE TIPOS DE PERIODOS
	 */
	public static final List<String> TYPES_PERIOD = List.of("d", "m", "a"); 
	
	/**
	 * LISTA DE ANOS DO ENISNO FUNDAMENTAL
	 */
	public static final List<Integer> ANOS_ENSINO_FUNDAMENTAL = Arrays.asList(1, 2, 3, 4,1, 5, 6, 7, 8, 9);
}
