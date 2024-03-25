package kalkulator;
import java.util.Stack;
public class Calculator extends CalculatorOperations{
public int accumulator;
public int[] Memory;
public Stack<Integer> stack;
public Calculator() {
	accumulator = 0;
	Memory = new int[16];
	stack = new Stack<>();
}
	public void setAccumulator( int value ) {
		accumulator = value;
	}
public int getAccumulator(){
		return accumulator;
	}
public int getMemory( int index ) {
	return Memory[index];
	}
public void accumulatorToMemory( int index ) {
	Memory[index] = accumulator;
}
public void addToAccumulator( int value ) {
	accumulator += value;
}
public void subtractFromAccumulator( int value ) {
	accumulator -= value;
}
public void addMemoryToAccumulator( int index ) {
	accumulator += Memory[index];
}
public void subtractMemoryFromAccumulator( int index ) {
	accumulator -= Memory[index];
}
public void reset() {
	accumulator = 0;
	for (int i = 0; i < Memory.length; i++) {
	    Memory[i] = 0;
}
	stack.clear();
}
	public void exchangeMemoryWithAccumulator( int index ) {
		int helping_int; //pomocnicza zmienna
	helping_int = Memory[index];
	Memory[index] = accumulator;
	accumulator = helping_int;
	}
	public void pushAccumulatorOnStack() {
		stack.push(accumulator);
	}
	public void pullAccumulatorFromStack() {
		accumulator= stack.pop();
	}
}
