const API_URL = `${(import.meta.env.VITE_API_URL || 'http://localhost:8081/api').replace(/\/$/, '')}/workers`;

async function request(workerId, options = {}) {
  const response = await fetch(`${API_URL}/${workerId}/health-profile`, {
    headers: { 'Content-Type': 'application/json' },
    ...options,
  });

  if (!response.ok) {
    const body = await response.json().catch(() => ({}));
    const error = new Error(body.message || 'Unable to process the health profile request.');
    error.status = response.status;
    throw error;
  }

  return response.json();
}

export const createHealthProfile = (workerId, profile) => request(workerId, {
  method: 'POST',
  body: JSON.stringify(profile),
});

export const getHealthProfile = (workerId) => request(workerId);

export const updateHealthProfile = (workerId, profile) => request(workerId, {
  method: 'PUT',
  body: JSON.stringify(profile),
});
