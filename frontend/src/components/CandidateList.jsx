import { useState, useEffect } from 'react';

/**
 * 후보 선택(selectedRestaurantId)을 내부 상태로 관리하고,
 * children({ selectedRestaurantId })로 VoteActions 등에 전달한다.
 */
export default function CandidateList({ candidates, children }) {
  const [selectedRestaurantId, setSelectedRestaurantId] = useState(null);

  useEffect(() => {
    setSelectedRestaurantId(null);
  }, [candidates]);

  return (
    <>
      {candidates.length > 0 && (
        <>
          <h2>후보</h2>
          <ul style={{ listStyle: 'none', padding: 0, marginTop: 8 }}>
            {candidates.map((r) => (
              <li
                key={r.id}
                style={{
                  marginBottom: 8,
                  padding: 8,
                  borderRadius: 6,
                  border: '1px solid #eee',
                }}
              >
                <label style={{ display: 'flex', gap: 8, alignItems: 'center' }}>
                  <input
                    type="radio"
                    name="restaurant"
                    checked={selectedRestaurantId === r.id}
                    onChange={() => setSelectedRestaurantId(r.id)}
                  />
                  <span>
                    <strong>{r.name}</strong> · {r.category} · {r.distance}분 · {r.priceRange}원대
                  </span>
                </label>
              </li>
            ))}
          </ul>
        </>
      )}
      {typeof children === 'function' ? children({ selectedRestaurantId }) : children}
    </>
  );
}
