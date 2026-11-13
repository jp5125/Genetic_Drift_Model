package geneticDriftExercise;

import java.util.*;
import sweep.SimStateSweep;
import sim.util.*;
import ec.util.*;
import sim.engine.*;

public class Environment extends SimStateSweep implements Steppable {
	
	int n = 20; //population size
	double p = 0.2; //proportion of type 1 alleles in the population
	Bag currentGeneration = new Bag();
	Bag nextGeneration = new Bag();
	
	//environment constructor
	public Environment(long seed, Class observer) 
	{
        super(seed, observer);
    }
	
	/*method to make agents at simulation genesis. For each agent in the simulation (controlled by variable n),
	 * the two alleles of each agent are are set to type 1 or 2 probabilistically based on p, which is the probability any 
	 * allele is type 1 in the population.
	 */
	public void makeAgents()
	{
		for(int i = 0; i < n; i++)
		{
			int alee1;
			int alee2;
			boolean a1 = random.nextBoolean(p);
			boolean a2 = random.nextBoolean(p);
			
			if(a1)
			{
				alee1 = 1;
				
			}
			else
			{
				alee1 = 2;
			}
			if(a2)
			{
				alee2 = 1;
			}
			else
			{
				alee2 = 2;
			}
			
			Agent a = new Agent(alee1, alee2);
			currentGeneration.add(a);
		}
	}
	
	/*
	 * Once the initial population of agents is generated, produceNextGen() is used at each subsequent
	 * time step to determine the genotype of the population in the next generation, as per 
	 * the assumptions of the Wright-Fischer Model. 
	 */
	
	public void produceNextGen()
	{
		int A1alleles = 0;
		int A2alleles = 0;
		
		
		for(Object individual:currentGeneration)
		{
			Agent a = (Agent) individual;
			
			if(a.genotype.equals("Heterozygous"))
			{
				A1alleles++;
				A2alleles++;
			}
			if(a.genotype.equals("Homozygous Dominant"))
			{
				A1alleles+= 2;
			}
			if(a.genotype.equals("Homozygous Recessive"))
			{
				A2alleles+= 2;
			}
		}
		
		for(int i = 0; i < n; i++)
		{
			
			int a1;
			int a2;
			boolean allele1 = random.nextBoolean(A1alleles / (2.0*n));
			boolean allele2 = random.nextBoolean(A1alleles / (2.0*n));
				
			if(allele1)
			{
				a1 = 1;
			}
			else
			{
				a1 = 2;
			}
			if(allele2)
			{
				a2 = 1;
			}
			else
			{
				a2 = 2;
			}
				
			Agent a = new Agent(a1, a2);
			nextGeneration.add(a);
		
		}
		
		currentGeneration.clear();
		
		for(Object individual : nextGeneration)
		{
			Agent a = (Agent) individual;
			currentGeneration.add(a);
			
		}
		nextGeneration.clear();
		
	}
	
	//tracks the allele frequency of the A1 type in each generation and returns it for visualization purposes
	public double getAllele1Frequency()
	{
		int count = 0;
		
		for(Object individual : currentGeneration)
		{
			Agent a = (Agent) individual;
			
			if(a.allele1 == 1) count++;
			if(a.allele2 == 1) count++;
		}
		
		return count/ (2.0 * currentGeneration.numObjs);
	}
	
	public void start()
	{
		super.start();
		currentGeneration.clear();
		nextGeneration.clear();
		makeAgents();
		schedule.scheduleRepeating(this);
	}
	
	@Override
	public void step(SimState state) {
		// TODO Auto-generated method stub
		produceNextGen();
	}


}
