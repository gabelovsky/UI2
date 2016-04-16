package view;



import java.awt.geom.Point2D;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Control {
	
	int count;
	void set_buttons(Button calc1_but, Button calc10_but, Button calc100_but, Button calcx_but, Button reset_but, Button def_but, Button read_but, GridPane grid, Label result_text){
		
	   Stage res_stage=new Stage();
		
		
		Algo algo=new Algo(result_text);
		count=0;
		
		calc1_but.setOnAction(event ->{
			
			if(!algo.inited){
				algo.init_genes();//genes->population
				algo.save_arr();//population->temp_pop
				algo.get_fitness();//fitness, destroys population
				algo.sort_pop();//sort based on fitness
			}else{
				algo.evolve();//temp_pop->new population
				algo.save_arr();//population->temp_pop
				algo.get_fitness();//calculate fitness, destroys population
				algo.sort_pop();
			
			}
			count++;
			
			if(algo.pop[0].fitness>algo.package_c*1000){
				result_text.setText("Found solution,"+(algo.pop[0].fitness-5000)+" moves\n"+"Calc count: "+count+"\n");
				algo.print_best();//temp_pop[0]->population[0], destroys population[0]
				
			}else{
				result_text.setText("Calc count: "+count+"\n");	
				algo.print_best();
			}
		});
		
		calc10_but.setOnAction(event ->{
			
			for(int i=0;i<10;i++){
				if(!algo.inited){
					algo.init_genes();
					algo.save_arr();
					algo.get_fitness();
					algo.sort_pop();
					
				}else{
					algo.evolve();
					algo.save_arr();
					algo.get_fitness();
					algo.sort_pop();
			
				}
				count++;	
				
				if(algo.pop[0].fitness>algo.package_c*1000){
					result_text.setText("Found solution,"+(algo.pop[0].fitness-5000)+" moves\n"+"Calc count: "+count+"\n");
					algo.print_best();
					break;
				}else{
					result_text.setText("Calc count: "+count+"\n");	
					algo.print_best();
				}
				
			}
			
			
		});
		
		calc100_but.setOnAction(event ->{
			for(int i=0;i<100;i++){
				if(!algo.inited){
					algo.init_genes();
					algo.save_arr();
					algo.get_fitness();
					algo.sort_pop();
					
				}else{
					algo.evolve();
					algo.save_arr();
					algo.get_fitness();
					algo.sort_pop();
					
				}
				count++;
				if(algo.pop[0].fitness>algo.package_c*1000){
					result_text.setText("Found solution,"+(algo.pop[0].fitness-5000)+" moves\n"+"Calc count: "+count+"\n");
					algo.print_best();
					break;
				}else{
					result_text.setText("Calc count: "+count+"\n");	
					algo.print_best();
				}
			}

		});

		calcx_but.setOnAction(event ->{
			if(!algo.inited){
				algo.init_genes();
				algo.save_arr();
				algo.get_fitness();
				algo.sort_pop();
				count++;}
			
			while(algo.pop[0].fitness<algo.package_c*1000){
				algo.evolve();
				algo.save_arr();
				algo.get_fitness();
				algo.sort_pop();
				count++;
				
			}
			result_text.setText("Found solution, "+(algo.pop[0].fitness-5000)+" moves\n"+"Calc count: "+count+"\n");
			algo.print_best();
		
		});
		
		reset_but.setOnAction(event->{
			count=0;
			result_text.setText("");
			algo.inited=false;
		});
		
		def_but.setOnAction(event ->{
			algo.start_packages.add(new Point2D.Double(1,4));
			algo.start_packages.add(new Point2D.Double(2,2));
			algo.start_packages.add(new Point2D.Double(4,1));
			algo.start_packages.add(new Point2D.Double(4,5));
			algo.start_packages.add(new Point2D.Double(6,3));
			algo.x_size=7;
			algo.y_size=7;
			algo.package_c=5;
			
			algo.start_poz=new Point2D.Double(3,6);
			algo.inited=false;
			count=0;
			result_text.setText("");
		});
	}
	
	
}
