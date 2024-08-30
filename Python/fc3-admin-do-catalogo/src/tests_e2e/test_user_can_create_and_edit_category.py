import pytest

from rest_framework.test import APIClient

@pytest.mark.django_db
class TestCreateAndEditCategory:
    def test_user_can_create_and_edit_category(self) -> None:
        api_client = APIClient()

        # Verificar que lista esta vazia
        list_response = api_client.get("/api/categories/")
        assert list_response.data == {"data": []}

        # Criar uma categoria
        create_response = api_client.post(
            "/api/categories/",
            data={
                "name": "Movie",
                "description": "Movie description"
            }
        )

        assert create_response.status_code == 201
        create_category_id = create_response.data["id"]

        #Verificar que categoria criada aparece na listagem
        list_response = api_client.get("/api/categories/")
        assert list_response.data == {
            "data": [
                {
                    "id": create_category_id,
                    "name": "Movie",
                    "description": "Movie description",
                    "is_active": True
                }
            ]
        }

        # Edita categoria criada
        update_request = api_client.put(
            f"/api/categories/{create_category_id}/",
            data= {
                "name": "Documentary",
                "description": "Documentary description",
                "is_active": False
            }
        )

        assert update_request.status_code == 204

        #Verifica que categoria editada aparece na listagem
        list_response = api_client.get("/api/categories/")
        assert list_response.data == {
            "data": [
                {
                    "id": create_category_id,
                    "name": "Documentary",
                    "description": "Documentary description",
                    "is_active": False
                }
            ]
        }

