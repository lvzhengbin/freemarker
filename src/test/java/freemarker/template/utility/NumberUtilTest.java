/*
 * Copyright 2014 Attila Szegedi, Daniel Dekany, Jonathan Revusky
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package freemarker.template.utility;

import java.math.BigDecimal;
import java.math.BigInteger;

import junit.framework.TestCase;

public class NumberUtilTest extends TestCase {

    public NumberUtilTest(String name) {
        super(name);
    }
    
    public void testGetSignum() {
        assertEquals(1, NumberUtil.getSignum(Double.valueOf(Double.POSITIVE_INFINITY)));
        assertEquals(1, NumberUtil.getSignum(Double.valueOf(3)));
        assertEquals(0, NumberUtil.getSignum(Double.valueOf(0)));
        assertEquals(-1, NumberUtil.getSignum(Double.valueOf(-3)));
        assertEquals(-1, NumberUtil.getSignum(Double.valueOf(Double.NEGATIVE_INFINITY)));
        try {
            NumberUtil.getSignum(Double.valueOf(Double.NaN));
            fail();
        } catch (ArithmeticException e) {
            // expected
        }
        
        assertEquals(1, NumberUtil.getSignum(Float.valueOf(Float.POSITIVE_INFINITY)));
        assertEquals(1, NumberUtil.getSignum(Float.valueOf(3)));
        assertEquals(0, NumberUtil.getSignum(Float.valueOf(0)));
        assertEquals(-1, NumberUtil.getSignum(Float.valueOf(-3)));
        assertEquals(-1, NumberUtil.getSignum(Float.valueOf(Float.NEGATIVE_INFINITY)));
        try {
            NumberUtil.getSignum(Float.valueOf(Float.NaN));
            fail();
        } catch (ArithmeticException e) {
            // expected
        }
        
        assertEquals(1, NumberUtil.getSignum(Long.valueOf(3)));
        assertEquals(0, NumberUtil.getSignum(Long.valueOf(0)));
        assertEquals(-1, NumberUtil.getSignum(Long.valueOf(-3)));
        
        assertEquals(1, NumberUtil.getSignum(Integer.valueOf(3)));
        assertEquals(0, NumberUtil.getSignum(Integer.valueOf(0)));
        assertEquals(-1, NumberUtil.getSignum(Integer.valueOf(-3)));
        
        assertEquals(1, NumberUtil.getSignum(Short.valueOf((short) 3)));
        assertEquals(0, NumberUtil.getSignum(Short.valueOf((short) 0)));
        assertEquals(-1, NumberUtil.getSignum(Short.valueOf((short) -3)));
        
        assertEquals(1, NumberUtil.getSignum(Byte.valueOf((byte) 3)));
        assertEquals(0, NumberUtil.getSignum(Byte.valueOf((byte) 0)));
        assertEquals(-1, NumberUtil.getSignum(Byte.valueOf((byte) -3)));
        
        assertEquals(1, NumberUtil.getSignum(BigDecimal.valueOf(3)));
        assertEquals(0, NumberUtil.getSignum(BigDecimal.valueOf(0)));
        assertEquals(-1, NumberUtil.getSignum(BigDecimal.valueOf(-3)));
        
        assertEquals(1, NumberUtil.getSignum(BigInteger.valueOf(3)));
        assertEquals(0, NumberUtil.getSignum(BigInteger.valueOf(0)));
        assertEquals(-1, NumberUtil.getSignum(BigInteger.valueOf(-3)));
    }
    
    public void testIsBigDecimalInteger() {
        BigDecimal n1 = new BigDecimal("1.125");
        if (n1.precision() != 4 || n1.scale() != 3) {
            throw new RuntimeException("Wrong: " +  n1);
        }
        BigDecimal n2 = new BigDecimal("1.125").subtract(new BigDecimal("0.005"));
        if (n2.precision() != 4 || n2.scale() != 3) {
            throw new RuntimeException("Wrong: " +  n2);
        }
        BigDecimal n3 = new BigDecimal("123");
        BigDecimal n4 = new BigDecimal("6000");
        BigDecimal n5 = new BigDecimal("1.12345").subtract(new BigDecimal("0.12345"));
        if (n5.precision() != 6 || n5.scale() != 5) {
            throw new RuntimeException("Wrong: " +  n5);
        }
        BigDecimal n6 = new BigDecimal("0"); 
        BigDecimal n7 = new BigDecimal("0.001").subtract(new BigDecimal("0.001")); 
        BigDecimal n8 = new BigDecimal("60000.5").subtract(new BigDecimal("0.5")); 
        BigDecimal n9 = new BigDecimal("6").movePointRight(3).setScale(-3);
        
        BigDecimal[] ns = new BigDecimal[] {
                n1, n2, n3, n4, n5, n6, n7, n8, n9,
                n1.negate(), n2.negate(), n3.negate(), n4.negate(), n5.negate(), n6.negate(), n7.negate(), n8.negate(),
                n9.negate(),
        };
        
        for (BigDecimal n : ns) {
            assertEquals(n.doubleValue() == n.longValue(), NumberUtil.isIntegerBigDecimal(n));
        }
        
    }

}
