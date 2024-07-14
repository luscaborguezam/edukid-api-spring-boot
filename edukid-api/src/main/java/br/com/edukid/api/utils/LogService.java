package br.com.edukid.api.utils;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Random;

/**
 * CLASSE DISPONIBILIZA METODOS PARA CRIAR ARQUIVO E PREENCHE-LO LOG'S 
 * @Author LUCAS BORGUEZAM
 * @Sice 14 de jul. de 2024
 */

@Service
public class LogService {
	
	@Autowired
	UtilsService utilsService;

}
