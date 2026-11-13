package geneticDriftExercise;



public class Agent 
{
	int allele1;
	int allele2;
	String genotype = null;
	
	
	
	/*
	 * Each agent in the model has 2 alleles at a hypothetical locus, and there are two 
	 * potential alleles that can fill each spot at the locus, A1 and A2.   
	 * 
	 */
	
	public Agent(int allele1, int allele2)
	{
		this.allele1 = allele1;
		this.allele2 = allele2;
		
		if(allele1 == 1)
		{
			if(allele2 == 2)
			{
				this.genotype = "Heterozygous";
			}
			else
			{
				this.genotype = "Homozygous Dominant";
			}
		}
		if(allele1 == 2)
		{
			if(allele2 == 2)
			{
				this.genotype = "Homozygous Recessive";
			}
			else
			{
				this.genotype = "Heterozygous";
			}
		}
		
	}

}
