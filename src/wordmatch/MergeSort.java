/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package wordmatch;

/**
 *
 * @author Ishwor
 */
import java.util.*;
public class MergeSort {
public static <T extends Comparable<T> > void mergeSort(List<T> list)
{
mergeSort(list, 0, list.size()-1);
}
private static <T extends Comparable<T> > void mergeSort
(List<T> list, int first, int last)
{
if (first < last)
{
int middle = (first + last) / 2;
mergeSort(list, first, middle);
mergeSort(list, middle+1, last);
merge(list, first, middle, last);
}
}
private static <T extends Comparable<T> > void merge(
List<T> list, int firstLeft, int lastLeft, int lastRight)
{
int firstRight = lastLeft + 1;
int numberOfElements = lastRight - firstLeft + 1;
List<T> tempA = new ArrayList<T>(numberOfElements);
int leftIndex = firstLeft;
int rightIndex = firstRight;
int index = 0;
while (leftIndex <= lastLeft && rightIndex <= lastRight)
{
if (list.get(leftIndex).
compareTo(list.get(rightIndex)) < 0)
{
tempA.add(index, list.get(leftIndex));
++leftIndex;
++index;
}
else
{
tempA.add(index, list.get(rightIndex));
++rightIndex;
++index;
}
}
while (leftIndex <= lastLeft)
{
tempA.add(index, list.get(leftIndex));
++leftIndex;
++index;
}
while (rightIndex <= lastRight)
{
tempA.add(index, list.get(rightIndex));
++rightIndex;
++index;
}
for (index = 0; index < numberOfElements; ++index)
{
list.set(firstLeft + index, tempA.get(index));
}
}
}
