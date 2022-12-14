/*
 * MIT License
 *
 * Copyright (c) 2022 Talsumi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package talsumi.marderlib.storage.fluid

import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant

interface FluidInventory {

	fun clear(tank: Int)

	fun tanks(): Int

	fun tankSize(tank: Int): Int

	fun isEmpty(tank: Int): Boolean

	fun getFluid(tank: Int): FluidStack

	fun setFluid(tank: Int, fluid: FluidStack)

	fun removeFluid(tank: Int, amount: Int): FluidStack

	fun removeFluid(tank: Int): FluidStack

	fun isValid(tank: Int, fluid: FluidVariant): Boolean = true

	fun markDirty()
}