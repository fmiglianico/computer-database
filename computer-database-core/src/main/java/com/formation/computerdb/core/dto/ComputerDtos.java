package com.formation.computerdb.core.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



/**
 * Class representing a Computer as a Data Transport Object
 * @author F. Miglianico
 */
@XmlRootElement(name= "computerDtos")
@XmlAccessorType(XmlAccessType.FIELD)
public class ComputerDtos {
	
	@XmlElement(name="computerDto", type=ComputerDto.class)
	private List<ComputerDto> computerDtos;

	public ComputerDtos() {
		setComputerDtos(new ArrayList<ComputerDto>());
	}

	public ComputerDtos(List<ComputerDto> computerDtos) {
		this.setComputerDtos(computerDtos);
	}

	/**
	 * @return the computerDtos
	 */
	public List<ComputerDto> getComputerDtos() {
		return computerDtos;
	}

	/**
	 * @param computerDtos the computerDtos to set
	 */
	public void setComputerDtos(List<ComputerDto> computerDtos) {
		this.computerDtos = computerDtos;
	}
}
