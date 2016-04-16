package view;


import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Algo {
	
	 public static final int GENE_C = 64;//don't change
	
	 
	 public static final int ELITE_C = 2;
	 public static final int NEW_C = 2;
	 public static final int POP_C = 10;
	 public static final int FIRST_CROSS = 17;
	 public static final int SECOND_CROSS = 47;
	 
    Population[] pop=new Population[POP_C];
	
	int evo_poz;
	Random rng = new Random(); 
	
	//this for example
	int package_c=5;
	int x_size=7;
	int y_size=7;
	Point2D start_poz=new Point2D.Double(3,6);
	Point2D curr_poz;
	
	List<Point2D> packages; 
	List<Point2D> start_packages = new ArrayList<Point2D>(); 
	
	Algo(){
		//for example
		start_packages.add(new Point2D.Double(1,4));
		start_packages.add(new Point2D.Double(2,2));
		start_packages.add(new Point2D.Double(4,1));
		start_packages.add(new Point2D.Double(4,5));
		start_packages.add(new Point2D.Double(6,3));
		x_size=7;
		y_size=7;
		package_c=5;
		
		/*
		init_genes();//allocate genes to population
		save_arr();//copy to temp_pop from population
		get_fitness();//calculate fitness, destroys population
		sort_pop();//sort based on fitness
		
		while(pop[0].fitness<package_c*1000){
			System.out.println("Curr best:"+pop[0].fitness);
		 evolve();//create new generation from temp_pop to population
		 save_arr();//copy new generation to temp_pop from population
		 get_fitness();//calculate fitness, destroys population
		 sort_pop();//sort based on fitness
		}
		
		for(int j=0;j<GENE_C;j++){
			pop[0].population[j]=pop[0].temp_pop[j];
		}
		System.out.println("found it:"+pop[0].fitness);
		print_best();
		System.out.println(pop[0].fitness);
		System.exit(0);*/
	}
	
	
	void init_genes(){
		
		for(int i=0;i<POP_C;i++){
			//allocate
			pop[i]=new Population();
			for(int j=0;j<GENE_C;j++)
				pop[i].population[j]=rng.nextInt(256);
		
		}
		
	}
	
	
	void save_arr(){
	for(int i=0;i<POP_C;i++)
	    for(int j=0;j<GENE_C;j++)
	    	pop[i].temp_pop[j]=	pop[i].population[j];
	}
	
	void get_fitness(){
		
		for(int i=0;i<POP_C;i++){
			packages= new ArrayList<Point2D>(start_packages);
			curr_poz= new Point2D.Double(start_poz.getX(),start_poz.getY());//position on field
			int curr_gene=0;//position in person
			int gene_value;//numeric value of curr_gene
			int jump_c=0;
			pop[i].fitness=0;
		
			while(jump_c<500 && pop[i].fitness<1000*package_c){
				
		
				if(curr_gene==GENE_C){
					curr_gene=0;
				}
				
			
				gene_value=pop[i].population[curr_gene];
				jump_c++;
				//0-63 inc
				if(gene_value<=63){	
					pop[i].population[gene_value]++;
					if(pop[i].population[gene_value]==256)
						pop[i].population[gene_value]=0;
					curr_gene++;
				}else
				//64-127 dec
				if(gene_value<=127){
					pop[i].population[gene_value-64]--;
				    if(pop[i].population[gene_value-64]==-1)
				    	pop[i].population[gene_value-64]=255;
				    curr_gene++;
				}else 
				//128-191 jump			
				if(gene_value<=191){
					curr_gene=gene_value-128;
				}else
				//192-255 do 
				{
					pop[i].fitness+=do_move(pop[i].population[gene_value-192],false);
					curr_gene++;
				}//ifs end
				
				
				//jump to start
			
				
			}//while end
		
			
		}//for end
	}
	
	int do_move(int gene,boolean print){
		 if(gene%2==0){
			 if(gene%4==0){
				 //up
				 if(curr_poz.getY()==0){
					 return 0;
				 }			 
				 curr_poz.setLocation(curr_poz.getX(), curr_poz.getY()-1);
				
				 if(is_package(curr_poz)){
					 if(print)
						 System.out.print("["+curr_poz.getX()+","+curr_poz.getY()+"] ");
					 packages.remove(curr_poz);
					 return 1000;
				 }
				
				 return 1;
			 }else{
				 //left
				 if(curr_poz.getX()==0){
					 return 0;
				 }
				 curr_poz.setLocation(curr_poz.getX()-1, curr_poz.getY());
				
				 if(is_package(curr_poz)){
					 if(print)
						 System.out.print("["+curr_poz.getX()+","+curr_poz.getY()+"] ");
					 packages.remove(curr_poz);
					 return 1000;
				 }
				 return 1;
			 }	 		
		 }else if(gene%5==0){
			 //right
			 if(curr_poz.getX()==x_size-1){
				 return 0;
			 }
			 curr_poz.setLocation(curr_poz.getX()+1, curr_poz.getY());
			
			 if(is_package(curr_poz)){
				 if(print)
					 System.out.print("["+curr_poz.getX()+","+curr_poz.getY()+"] ");
				 packages.remove(curr_poz);
				 return 1000;
			 }
			 return 1;
		 }else{
			 //down
			 if(curr_poz.getY()==y_size-1){
				 return 0;
			 }
			 curr_poz.setLocation(curr_poz.getX(), curr_poz.getY()+1);
			
			 if(is_package(curr_poz)){
				 if(print)
					 System.out.print("["+curr_poz.getX()+","+curr_poz.getY()+"] ");
				 packages.remove(curr_poz);
				 return 1000;
			 }
			 return 1;
		 }
		 
	}
	
	public boolean is_package(Point2D pos) { 
		
	    return packages.contains(pos); 
	}
	
	void sort_pop(){
		Arrays.sort(pop, new Comparator<Population>() {
	        @Override
	        public int compare(Population o1, Population o2) {
	            return o1.fitness.compareTo(o2.fitness);
	        }
	 }.reversed());
	
	}
	
	void evolve(){
		int first=0,second=0;
		int fit_sum,rand,rand_sum;
		//copy elites
		for(int i=0;i<ELITE_C;i++)
			for(int j=0;j<GENE_C;j++){
				pop[i].population[j]=pop[i].temp_pop[j];
				pop[i].fitness=1;
			}
		evo_poz=ELITE_C;
		
		
		for(int j=0;j<(POP_C-ELITE_C-NEW_C)/2;j++){
		fit_sum=0;
		for(int i=0;i<POP_C;i++){
			fit_sum+=pop[i].fitness;
		}
		
		rand=rng.nextInt(fit_sum);
		rand_sum=0;
		for(int i=0;i<POP_C;i++){
			rand_sum+=pop[i].fitness;
			if(rand_sum>rand){
				first=i;
				fit_sum=fit_sum-pop[i].fitness+1;
				pop[i].fitness=1;		
				break;
			}
		}
		rand=rng.nextInt(fit_sum);
		rand_sum=0;
		for(int i=0;i<POP_C;i++){
			rand_sum+=pop[i].fitness;
			if(rand_sum>rand){
				second=i;
				pop[i].fitness=1;		
				break;
			}
		}
		crossover(first,second);
		//two new persons
		evo_poz+=2;
		}
		
		for(int i=0;i<NEW_C;i++){
			for(int j=0;j<GENE_C;j++)
			  pop[evo_poz].population[j]=rng.nextInt(256);
			evo_poz++;
		}
		
		
		
	}
	
	void crossover(int first,int second){
		
		//copy first third
		for(int i=0;i<FIRST_CROSS;i++){
			pop[evo_poz].population[i]=pop[first].temp_pop[i];
			pop[evo_poz+1].population[i]=pop[second].temp_pop[i];
		}
		
		//copy second third//swap
		for(int i=FIRST_CROSS;i<SECOND_CROSS;i++){
			pop[evo_poz].population[i]=pop[second].temp_pop[i];
			pop[evo_poz+1].population[i]=pop[first].temp_pop[i];
		}
		
		//copy last third
		for(int i=SECOND_CROSS;i<POP_C;i++){
			pop[evo_poz].population[i]=pop[first].temp_pop[i];
			pop[evo_poz+1].population[i]=pop[second].temp_pop[i];
		}
		
	}
	
	void print_best(){
		packages= new ArrayList<Point2D>(start_packages);
		curr_poz= new Point2D.Double(start_poz.getX(),start_poz.getY());
		int curr_gene=0;
		int gene_value;
		
		pop[0].fitness=0;
		while(pop[0].fitness<1000*package_c){
			
			if(curr_gene==GENE_C){
				curr_gene=0;
			}
			gene_value=pop[0].population[curr_gene];
			//0-63 inc
			if(gene_value<=63){	
				pop[0].population[gene_value]++;
				if(pop[0].population[gene_value]==256)
					pop[0].population[gene_value]=0;
				curr_gene++;
			}else
			//64-127 dec
			if(gene_value<=127){
				pop[0].population[gene_value-64]--;
			    if(pop[0].population[gene_value-64]==-1)
			    	pop[0].population[gene_value-64]=255;
			    curr_gene++;
			}else 
			//128-191 jump			
			if(gene_value<=191){
				curr_gene=gene_value-128;
			}else
			//192-255 do 
			{
				pop[0].fitness+=do_move(pop[0].population[gene_value-192],true);
				curr_gene++;
			}
	}//while end
	}
	
}
