/*
 * Copyright 2010, Strategic Gains, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 *
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.strategicgains.repoexpress.memory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.strategicgains.repoexpress.AbstractObservableRepository;
import com.strategicgains.repoexpress.domain.Identifiable;
import com.strategicgains.repoexpress.domain.Identifier;
import com.strategicgains.repoexpress.exception.DuplicateItemException;
import com.strategicgains.repoexpress.exception.ItemNotFoundException;

/**
 * An extremely basic in-memory repository backed by a Map implementation.  Filtering, sorting, etc.
 * are not supported, so methods operate over the entire collection.
 * 
 * @author toddf
 * @since Oct 12, 2010
 */
public abstract class InMemoryRepository<T extends Identifiable>
extends AbstractObservableRepository<T>
{
	private static long nextId = 0;
	protected Map<Identifier, T> items = new ConcurrentHashMap<Identifier, T>();
	
	@Override
	public boolean exists(Identifier id)
	{
		return items.containsKey(id);
	}

	@Override
	public T doCreate(T item)
	{
		if (hasId(item))
		{
			try
			{
				read(item.getId());
				throw new DuplicateItemException(item.getClass().getSimpleName() + " ID already exists: " + item.getId().toString());
			}
			catch(ItemNotFoundException e)
			{
				// expected.
			}
		}
		else
		{
			item.setId(new Identifier(item.getClass().getSimpleName(), ++nextId));
		}

		items.put(item.getId(), item);
		return item;
	}

    @Override
    public T doRead(Identifier id)
    {
    	T b = items.get(id);
    	
    	if (b == null)
    	{
    		throw new ItemNotFoundException("ID not found: " + id.toString());
    	}

    	return b;
    }

    @Override
    public T doUpdate(T item)
    {
    	items.put(item.getId(), item);
    	return item;
    }

    @Override
    public void doDelete(T object)
    {
    	T item = items.remove(object.getId());

    	if (item == null)
    	{
    		throw new ItemNotFoundException("ID not found: " + object.getId().toString());
    	}
    }
}
