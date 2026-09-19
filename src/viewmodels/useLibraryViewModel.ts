import { useMemo, useState } from 'react';
import { content } from '../models/content';
import { ContentItem } from '../types';

export function useLibraryViewModel() {
  const [category, setCategory] = useState('All');
  const [query, setQuery] = useState('');
  const categories = ['All', 'Mental Wellbeing', 'HIV/AIDS', 'Relationships'];

  const items = useMemo<ContentItem[]>(() => {
    return content.filter((item) => {
      const matchCategory = category === 'All' || item.category === category;
      const q = query.trim().toLowerCase();
      return matchCategory && (!q || `${item.title} ${item.summary}`.toLowerCase().includes(q));
    });
  }, [category, query]);

  return { categories, category, setCategory, query, setQuery, items };
}
