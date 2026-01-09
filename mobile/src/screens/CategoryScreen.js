import React, {useState, useEffect} from 'react';
import {
  View,
  Text,
  StyleSheet,
  ScrollView,
  Alert,
  TouchableOpacity,
} from 'react-native';
import Icon from 'react-native-vector-icons/MaterialIcons';
import api from '../config/api';
import Button from '../components/Button';
import Input from '../components/Input';
import Card from '../components/Card';

const CategoryScreen = () => {
  const [categories, setCategories] = useState([]);
  const [newCategory, setNewCategory] = useState('');
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    loadCategories();
  }, []);

  const loadCategories = async () => {
    try {
      const response = await api.get('/categories');
      setCategories(response.data);
    } catch (error) {
      Alert.alert('Error', 'Failed to load categories');
    }
  };

  const addCategory = async () => {
    if (!newCategory.trim()) return;

    setLoading(true);
    try {
      await api.post('/categories', {name: newCategory});
      setNewCategory('');
      loadCategories();
    } catch (error) {
      Alert.alert('Error', 'Failed to add category');
    } finally {
      setLoading(false);
    }
  };

  const deleteCategory = id => {
    Alert.alert('Delete Category', 'Are you sure?', [
      {text: 'Cancel', style: 'cancel'},
      {
        text: 'Delete',
        onPress: async () => {
          try {
            await api.delete(`/categories/${id}`);
            loadCategories();
          } catch (error) {
            Alert.alert('Error', 'Failed to delete category');
          }
        },
        style: 'destructive',
      },
    ]);
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.content}>
        <Card style={styles.addCard}>
          <Text style={styles.title}>Add New Category</Text>
          <View style={styles.inputRow}>
            <View style={{flex: 1}}>
              <Input
                value={newCategory}
                onChangeText={setNewCategory}
                placeholder="Category name"
                icon="category"
              />
            </View>
            <Button
              title="Add"
              onPress={addCategory}
              loading={loading}
              style={styles.addButton}
            />
          </View>
        </Card>

        <Text style={styles.sectionTitle}>
          Your Categories ({categories.length})
        </Text>

        {categories.length === 0 ? (
          <Card>
            <View style={styles.emptyState}>
              <Icon name="category" size={48} color="#9CA3AF" />
              <Text style={styles.emptyText}>No categories yet</Text>
            </View>
          </Card>
        ) : (
          categories.map(category => (
            <Card key={category.id} style={styles.categoryCard}>
              <View style={styles.categoryRow}>
                <View style={styles.categoryLeft}>
                  <View style={styles.iconContainer}>
                    <Icon name="category" size={20} color="#8B5CF6" />
                  </View>
                  <Text style={styles.categoryName}>{category.name}</Text>
                </View>
                <TouchableOpacity onPress={() => deleteCategory(category.id)}>
                  <Icon name="delete" size={24} color="#EF4444" />
                </TouchableOpacity>
              </View>
            </Card>
          ))
        )}
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#F3F4F6',
  },
  content: {
    padding: 16,
  },
  addCard: {
    backgroundColor: '#EDE9FE',
    marginBottom: 16,
  },
  title: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 12,
  },
  inputRow: {
    flexDirection: 'row',
    gap: 12,
    alignItems: 'flex-start',
  },
  addButton: {
    width: 80,
    marginTop: 24,
  },
  sectionTitle: {
    fontSize: 18,
    fontWeight: 'bold',
    marginBottom: 12,
  },
  categoryCard: {
    marginBottom: 12,
  },
  categoryRow: {
    flexDirection: 'row',
    justifyContent: 'space-between',
    alignItems: 'center',
  },
  categoryLeft: {
    flexDirection: 'row',
    alignItems: 'center',
    gap: 12,
  },
  iconContainer: {
    width: 40,
    height: 40,
    borderRadius: 8,
    backgroundColor: '#EDE9FE',
    justifyContent: 'center',
    alignItems: 'center',
  },
  categoryName: {
    fontSize: 16,
    fontWeight: '600',
  },
  emptyState: {
    alignItems: 'center',
    padding: 32,
  },
  emptyText: {
    fontSize: 16,
    color: '#6B7280',
    marginTop: 12,
  },
});

export default CategoryScreen;
