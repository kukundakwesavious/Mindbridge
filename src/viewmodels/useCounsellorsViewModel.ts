import { useMemo, useState } from 'react';
import { counsellors } from '../models/counsellors';
import { Counsellor } from '../types';

export function useCounsellorsViewModel() {
  const [filter, setFilter] = useState('All');
  const [query, setQuery] = useState('');

  const filtered = useMemo<Counsellor[]>(() => {
    return counsellors.filter((c) => {
      const matchesFilter =
        filter === 'All' ||
        (filter === 'Available' ? c.available : c.languages.includes(filter));
      const q = query.trim().toLowerCase();
      const matchesQuery =
        !q || `${c.name} ${c.role} ${c.speciality}`.toLowerCase().includes(q);
      return matchesFilter && matchesQuery;
    });
  }, [filter, query]);

  return { filter, setFilter, query, setQuery, counsellors: filtered };
}
